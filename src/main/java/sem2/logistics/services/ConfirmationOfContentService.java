package sem2.logistics.services;

import com.bloxbean.cardano.client.account.Account;
import com.bloxbean.cardano.client.backend.api.BackendService;
import com.bloxbean.cardano.client.backend.api.BlockService;
import com.bloxbean.cardano.client.backend.api.helper.FeeCalculationService;
import com.bloxbean.cardano.client.backend.api.helper.TransactionHelperService;
import com.bloxbean.cardano.client.backend.exception.ApiException;
import com.bloxbean.cardano.client.backend.factory.BackendFactory;
import com.bloxbean.cardano.client.backend.impl.blockfrost.common.Constants;
import com.bloxbean.cardano.client.backend.model.Block;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.common.CardanoConstants;
import com.bloxbean.cardano.client.exception.AddressExcepion;
import com.bloxbean.cardano.client.exception.CborSerializationException;
import com.bloxbean.cardano.client.metadata.Metadata;
import com.bloxbean.cardano.client.metadata.cbor.CBORMetadata;
import com.bloxbean.cardano.client.metadata.cbor.CBORMetadataMap;
import com.bloxbean.cardano.client.transaction.model.PaymentTransaction;
import com.bloxbean.cardano.client.transaction.model.TransactionDetailsParams;
import org.springframework.stereotype.Service;
import sem2.logistics.dtos.request.ConfirmationOfContentRequestDto;
import sem2.logistics.dtos.response.ConfirmationOfContentResponseDto;
import sem2.logistics.dtos.response.ProductResponseDto;
import sem2.logistics.models.ConfirmationOfContent;
import sem2.logistics.models.Product;
import sem2.logistics.repositories.ConfirmationOfContentRepository;
import sem2.logistics.util.State;

import java.math.BigInteger;

@Service
public class ConfirmationOfContentService {

    String PROJECT_ID = "previewDhxaLLDdGBkLbhxHMbREP6T36xGFlYn1";
    private final ConfirmationOfContentRepository confirmationOfContentRepository;
    private final ProducerService producerService;
    private final ProductService productService;
    private final BackendService backendService;
    private final BlockService blockService;
    private final FeeCalculationService feeCalculationService;
    private final TransactionHelperService transactionHelperService;

    public ConfirmationOfContentService(ConfirmationOfContentRepository confirmationOfContentRepository, ProducerService producerService, ProductService productService) {
        this.confirmationOfContentRepository = confirmationOfContentRepository;
        this.producerService = producerService;
        this.productService = productService;
        backendService = BackendFactory.getBlockfrostBackendService(Constants.BLOCKFROST_TESTNET_URL, PROJECT_ID);
        blockService = backendService.getBlockService();
        feeCalculationService = backendService.getFeeCalculationService();
        transactionHelperService = backendService.getTransactionHelperService();
    }

    public ConfirmationOfContentResponseDto createConfirmationOfContent(ConfirmationOfContentRequestDto confirmationOfContentRequestDto) throws AddressExcepion, CborSerializationException, ApiException {

        ProductResponseDto product = productService.getProduct(confirmationOfContentRequestDto.getProductId());
        ProductResponseDto component = productService.getProduct(confirmationOfContentRequestDto.getComponentId());

//        if (product.getState() != State.VERIFIED || product.getState() != State.VERIFIED) {
//            throw new RuntimeException("Products are not both verified.");
//        }

        Integer productProducerId = product.getProducerId();
        Integer componentProducerId = component.getProducerId();

        String productProducerAddress = producerService.getProducer(productProducerId).getAddress();
        Account componentProducerAccount = producerService.getAccount(componentProducerId);

        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .sender(componentProducerAccount)
                .receiver(productProducerAddress)
                .amount(BigInteger.valueOf(1))
                .unit(CardanoConstants.LOVELACE)
                .build();

        long ttl = blockService.getLastestBlock().getValue().getSlot() + 1000;

        TransactionDetailsParams detailsParams = TransactionDetailsParams.builder()
                .ttl(ttl)
                .build();

        Metadata metadata = generateMetadata(confirmationOfContentRequestDto);

        BigInteger fee = feeCalculationService.calculateFee(paymentTransaction, detailsParams, metadata);

        paymentTransaction.setFee(fee);

        Result<String> result = transactionHelperService.transfer(paymentTransaction, detailsParams);

        if (result.isSuccessful()) {
            System.out.println("Transaction Id: " + result.getValue());
        } else {
            System.out.println("Transaction failed: " + result);
            return null;
        }

        // TODO: check if this is really hash
        String blockHash = result.getValue();

        ConfirmationOfContent confirmationOfContent = new ConfirmationOfContent(confirmationOfContentRequestDto.getProductId(), confirmationOfContentRequestDto.getComponentId(), confirmationOfContentRequestDto.getState(), blockHash);
        confirmationOfContent = confirmationOfContentRepository.save(confirmationOfContent);

        return new ConfirmationOfContentResponseDto(confirmationOfContent.getConfirmationOfContentId(), confirmationOfContent.getProductId(), confirmationOfContent.getComponentId(), confirmationOfContent.getState(), confirmationOfContent.getBlockHash());

    }

    private Metadata generateMetadata(ConfirmationOfContentRequestDto confirmationOfContentRequestDto) {

        CBORMetadataMap metadataMap = new CBORMetadataMap()
                .put("productId", confirmationOfContentRequestDto.getProductId().toString())
                .put("componentId", confirmationOfContentRequestDto.getComponentId().toString())
                .put("state", confirmationOfContentRequestDto.getState().toString());

        Metadata metadata = new CBORMetadata()
                .put(new BigInteger("670001"), metadataMap);

        return metadata;
    }

    public ConfirmationOfContentResponseDto getConfirmationOfContent(Integer confirmationOfContentId) {
        ConfirmationOfContent confirmationOfContent = confirmationOfContentRepository.getReferenceById(confirmationOfContentId);
        return new ConfirmationOfContentResponseDto(confirmationOfContent.getConfirmationOfContentId(), confirmationOfContent.getProductId(), confirmationOfContent.getComponentId(), confirmationOfContent.getState(), confirmationOfContent.getBlockHash());
    }

    public Result<Block> getBlockByHash(String hash) throws ApiException {
        return blockService.getBlockByHash(hash);
    }

    public ConfirmationOfContentResponseDto getConfirmationOfContentByProductId(Integer productId) {
        ConfirmationOfContent confirmationOfContent = confirmationOfContentRepository.getConfirmationOfContentByProductId(productId);
        return new ConfirmationOfContentResponseDto(confirmationOfContent.getConfirmationOfContentId(), confirmationOfContent.getProductId(), confirmationOfContent.getComponentId(), confirmationOfContent.getState(), confirmationOfContent.getBlockHash());
    }


}
