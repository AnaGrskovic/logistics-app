package sem2.logistics.services;

import com.bloxbean.cardano.client.account.Account;
import com.bloxbean.cardano.client.common.model.Networks;
import org.springframework.stereotype.Service;
import sem2.logistics.dtos.response.ProducerResponseDto;
import sem2.logistics.dtos.response.ProductResponseDto;
import sem2.logistics.models.Producer;
import sem2.logistics.models.Product;
import sem2.logistics.repositories.ProducerRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private Map accounts = new HashMap<Integer, Account>();

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public ProducerResponseDto createProducer() {

        Account account = new Account(Networks.testnet());

        Producer producer = new Producer(account.baseAddress());

        producer = producerRepository.save(producer);

        accounts.put(producer.getProducerId(), account);

        return new ProducerResponseDto(producer.getProducerId(), producer.getAddress());
    }

    public ProducerResponseDto getProducer(Integer producerId) {
        Producer producer = producerRepository.getReferenceById(producerId);
        return new ProducerResponseDto(producer.getProducerId(), producer.getAddress());
    }

    public List<ProducerResponseDto> getProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producers.stream()
                .map(producer -> new ProducerResponseDto(producer.getProducerId(), producer.getAddress()))
                .collect(Collectors.toList());
    }

    public Account getAccount(Integer producerId) {
        return (Account) accounts.get(producerId);
    }

}