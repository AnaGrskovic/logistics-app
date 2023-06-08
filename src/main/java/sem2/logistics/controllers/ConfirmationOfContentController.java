package sem2.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sem2.logistics.dtos.request.ConfirmationOfContentRequestDto;
import sem2.logistics.dtos.request.ProductRequestDto;
import sem2.logistics.dtos.response.ConfirmationOfContentResponseDto;
import sem2.logistics.dtos.response.ProducerResponseDto;
import sem2.logistics.dtos.response.ProductResponseDto;
import sem2.logistics.services.ConfirmationOfContentService;

@Controller
@RequestMapping("/confirmation-of-content")
public class ConfirmationOfContentController {

    @Autowired
    private ConfirmationOfContentService confirmationOfContentService;

    @GetMapping("/form")
    public String createConfirmationOfContentForm(Model model) {
        model.addAttribute("confirmationOfContent", new ConfirmationOfContentRequestDto());
        return "create-confirmation-of-content";
    }

    @PostMapping("/form")
    public String createConfirmationOfContentSubmit(@ModelAttribute ConfirmationOfContentRequestDto confirmationOfContentRequestDto) {
        try {
            ConfirmationOfContentResponseDto confirmationOfContentResponseDto = confirmationOfContentService.createConfirmationOfContent(confirmationOfContentRequestDto);
            return "successfully-created-confirmation-of-content";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping("/{confirmationOfContentId}")
    public String getConfirmationOfContent(Model model, @PathVariable Integer confirmationOfContentId) {
        try {
            ConfirmationOfContentResponseDto confirmationOfContentResponseDto = confirmationOfContentService.getConfirmationOfContent(confirmationOfContentId);
            model.addAttribute("confirmationOfContent", confirmationOfContentResponseDto);
            return "get-confirmation-of-content";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping("/block/{blockHash}")
    public ResponseEntity<Object> getConfirmationOfContentBlock(@PathVariable String blockHash) {
        try {
            return ResponseEntity.ok(confirmationOfContentService.getBlockByHash(blockHash));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/product-id/{productId}")
    public String getConfirmationOfContentByProductId(Model model, @PathVariable Integer productId) {
        try {
            ConfirmationOfContentResponseDto confirmationOfContentResponseDto = confirmationOfContentService.getConfirmationOfContentByProductId(productId);
            model.addAttribute("confirmationOfContent", confirmationOfContentResponseDto);
            return "get-confirmation-of-content";
        } catch (Exception ex) {
            return "error";
        }
    }

}

