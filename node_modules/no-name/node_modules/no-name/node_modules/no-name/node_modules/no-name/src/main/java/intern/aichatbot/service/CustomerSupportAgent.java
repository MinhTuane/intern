package intern.aichatbot.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;


public interface CustomerSupportAgent {

    @SystemMessage("""
            You are a chat bot of a selling product company name NoName. You are here to help customer and answer customer question.
            """)
    TokenStream chat(@MemoryId String chatId, @UserMessage String message);
}

