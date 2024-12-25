import { Subscription as Subscription_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
function chat_1(chatId: string | undefined, question: string | undefined): Subscription_1<string | undefined> { return client_1.subscribe("AssistanceService", "chat", { chatId, question }); }
export { chat_1 as chat };
