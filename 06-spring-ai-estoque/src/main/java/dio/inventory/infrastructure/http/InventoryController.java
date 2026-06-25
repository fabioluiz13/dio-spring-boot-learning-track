package dio.inventory.infrastructure.http;

import dio.inventory.application.ListProductsByLocationUseCase;
import dio.inventory.application.UpdateStockUseCase;
import dio.inventory.domain.StorageLocation;
import dio.inventory.infrastructure.http.request.UpdateStockRequest;
import dio.inventory.infrastructure.http.response.StockResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/products")
public class InventoryController {
    private final UpdateStockUseCase updateStockUseCase;
    private final ListProductsByLocationUseCase listProductsByLocationUseCase;

    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;

    public InventoryController(UpdateStockUseCase updateStockUseCase,
                               ListProductsByLocationUseCase listProductsByLocationUseCase,
                               TranscriptionModel transcriptionModel,
                               @Value("classpath:prompts/stock-system-message.st") Resource systemPrompt,
                               ChatClient.Builder chatClientBuilder,
                               TextToSpeechModel textToSpeechModel) throws IOException {
        this.updateStockUseCase = updateStockUseCase;
        this.listProductsByLocationUseCase = listProductsByLocationUseCase;
        this.transcriptionModel = transcriptionModel;
        this.chatClient = chatClientBuilder
                .defaultSystem(systemPrompt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(updateStockUseCase, listProductsByLocationUseCase)
                .build();
        this.textToSpeechModel = textToSpeechModel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse createProduct(@RequestBody UpdateStockRequest request) {
        var output = updateStockUseCase.execute(request.toInput());
        return StockResponse.from(output);
    }

    @GetMapping("/{location}")
    public List<StockResponse> listByLocation(@PathVariable StorageLocation location) {
        return listProductsByLocationUseCase.execute(location)
                .stream()
                .map(StockResponse::from)
                .toList();
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    ResponseEntity<Resource> processVoiceCommand(@RequestParam("file") MultipartFile file) {
        var userMessage = transcriptionModel.transcribe(file.getResource());
        var result = chatClient.prompt().user(userMessage).call().content();

        byte[] audio = textToSpeechModel.call(result);
        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("response.mp3")
                                .build()
                                .toString())
                .body(resource);
    }
}
