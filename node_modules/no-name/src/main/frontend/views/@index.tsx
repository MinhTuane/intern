import 'semantic-ui-css/semantic.min.css'
import { Container, Grid, GridColumn, Segment } from 'semantic-ui-react'
import { v4 as uuid } from 'uuid';
import React, { useState } from 'react'
import { MessageItem } from 'Frontend/components/Message';
import { AssistanceService } from 'Frontend/generated/endpoints';
import { MessageInput } from "@vaadin/react-components/MessageInput";
import AiChat from 'Frontend/components/AiChat';
import ChatHeader from 'Frontend/components/ChatHeader';

export default function HomeView() {
  const [chatId, setChatId] = useState(uuid());
  const [working, setWorking] = useState(false);
  const [messages, setMessages] = useState<MessageItem[]>([{
    role: 'assistant',
    content: 'Welcome to No Name! How can I help you?'
  }]);

  function addMessage(message: MessageItem) {
    setMessages(messages => [...messages, message]);
  }

  function appendToLatestMessage(chunk: string) {
    setMessages(messages => {
      const latestMessage = messages[messages.length - 1];
      latestMessage.content += chunk;
      return [...messages.slice(0, -1), latestMessage];
    });
  }

  async function sendMessage(message: string) {
    setWorking(true);
    addMessage({
      role: 'user',
      content: message
    });
    let first = true;
    AssistanceService.chat(chatId, message)
      .onNext(token => {
        if (first && token) {
          addMessage({
            role: 'assistant',
            content: token
          });

          first = false;
        } else {
          appendToLatestMessage(token!);
        }
      })
      .onError(() => setWorking(false))
      .onComplete(() => setWorking(false));
  }

  return (
    <Container  className='main'>
      <ChatHeader/>
      <AiChat messages={messages} working={working} />
      <MessageInput onSubmit={e => sendMessage(e.detail.value)} className="px-0" />
    </Container>
  );
}