package intern.aichatbot.service;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@AnonymousAllowed
@BrowserCallable
@AllArgsConstructor
public class AssistanceService {
    private final CustomerSupportAgent agent;

    public Flux<String> chat(String chatId,String question) {
         Sinks.Many<String> sinks = Sinks.many().unicast().onBackpressureBuffer();

         agent.chat(chatId, question)
                .onNext(sinks::tryEmitNext)
                .onComplete(c -> sinks.tryEmitComplete())
                .onError(sinks::tryEmitError)
                .start();

         return sinks.asFlux();
    }
}
