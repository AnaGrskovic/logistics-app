package sem2.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sem2.logistics.dtos.response.ProducerResponseDto;
import sem2.logistics.services.ProducerService;
import java.util.List;

@Controller
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/form")
    public String createProducerForm() {
        return "create-producer";
    }

    @PostMapping("/form")
    public String createProducerSubmit() {
        try {
            ProducerResponseDto producerResponseDto = producerService.createProducer();
            return "successfully-created-producer";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping("/{producerId}")
    public String getProducer(Model model, @PathVariable Integer producerId) {
        try {
            ProducerResponseDto producerResponseDto = producerService.getProducer(producerId);
            model.addAttribute("producer", producerResponseDto);
            return "get-producer";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping()
    public String getProducers(Model model) {
        try {
            List<ProducerResponseDto> producerResponseDtos = producerService.getProducers();
            model.addAttribute("producers", producerResponseDtos);
            return "get-producers";
        } catch (Exception ex) {
            return "error";
        }
    }

}
