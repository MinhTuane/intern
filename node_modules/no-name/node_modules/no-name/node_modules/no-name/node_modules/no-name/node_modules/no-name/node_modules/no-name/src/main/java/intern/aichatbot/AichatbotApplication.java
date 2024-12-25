package intern.aichatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import com.vaadin.flow.component.page.AppShellConfigurator;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import intern.aichatbot.service.CustomerSupportAgent;

import com.vaadin.flow.theme.Theme;

import intern.aichatbot.service.impl.BookingTools;
import intern.aichatbot.service.impl.ProductTools;

@SpringBootApplication
@EntityScan(basePackages = "intern.aichatbot.entity")
@Theme("my-theme")
public class AichatbotApplication implements AppShellConfigurator {

    @Bean
    StreamingChatLanguageModel streamingChatLanguageModel(@Value("${openai.api.key}") String apiKey) {
        return OpenAiStreamingChatModel.builder().apiKey(apiKey).modelName("gpt-4-1106-preview").build();
    }

    @Bean
    Tokenizer tokenizer() {
        return new OpenAiTokenizer(GPT_4);
    }

    @Bean
    CustomerSupportAgent customerSupportAgent(StreamingChatLanguageModel streamingChatLanguageModel, 
												Tokenizer tokenizer, Retriever<TextSegment> retriever,
                                                BookingTools bookingTools,ProductTools productTools) {
        return AiServices.builder(CustomerSupportAgent.class)
		.streamingChatLanguageModel(streamingChatLanguageModel)
		.chatMemoryProvider(chatId -> TokenWindowChatMemory.builder()
		.id(chatId).maxTokens(500, tokenizer).build())
		.retriever(retriever)
        .tools(bookingTools)
        .tools(productTools)
        .build();
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    Retriever<TextSegment> retriever(EmbeddingModel embeddingModel, EmbeddingStore embeddingStore) {
        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, 1, 0.6);
    }

    @Bean
    CommandLineRunner docstoEmbeddings(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore, Tokenizer tokenizer, ResourceLoader loader) {
        return args -> {
            var resource = loader.getResource("classpath:term-of-service.txt");
            var doc = loadDocument(resource.getFile().toPath());
            var splitter = DocumentSplitters.recursive(100, 0, tokenizer);
            var ingestor = EmbeddingStoreIngestor.builder().embeddingModel(embeddingModel).embeddingStore(embeddingStore).documentSplitter(splitter).build();
            ingestor.ingest(doc);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(AichatbotApplication.class, args);
    }
}
