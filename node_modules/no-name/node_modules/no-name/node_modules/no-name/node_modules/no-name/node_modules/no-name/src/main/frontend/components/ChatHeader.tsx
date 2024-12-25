import React from "react";
import { Header, Icon } from "semantic-ui-react";

const ChatHeader = () => {
    return (
        <Header as="h2" textAlign="center" >
            <Icon name="chat" />
            AI Chatbot
        </Header>
    );
};

export default ChatHeader;