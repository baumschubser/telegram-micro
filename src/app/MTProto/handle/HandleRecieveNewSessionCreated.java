package mtproto.handle;

import java.io.IOException;

import mtproto.MTProtoConnection;
import mtproto.Response;
import mtproto.EncryptedResponse;
import mtproto.recieve.RecieveNewSessionCreated;
import mtproto.send.service.SendMsgsAck;
import mtproto.CombinatorIds;
import mtproto.send.auth.SendSendCode;
import mtproto.send.help.SendGetConfig;


public class HandleRecieveNewSessionCreated extends MTProtoCallback {
  public HandleRecieveNewSessionCreated(MTProtoConnection connection) {
    super(CombinatorIds.new_session_created, connection);
  }
  
  public void execute(Response response) {
    EncryptedResponse encrypted_response = (EncryptedResponse)response;
    RecieveNewSessionCreated recieve_new_session_created = RecieveNewSessionCreated.from_encrypted_message(encrypted_response);
    (new SendMsgsAck(new long[] {encrypted_response.message_id})).send(connection);
    connection.setMTProtoHeader(
      139,
      487068,
      "Sun Java Wireless Toolkit Emulator",
      "2.5.2",
      "indev",
      "en",
      "en",
      "en"
    );
    (new SendGetConfig()).send(connection);
    /*
    try {
      System.out.println("We're done, now closing the connection");
      connection.close();
    } catch (IOException e) {
      System.out.println("Error closing connection");
      e.printStackTrace();
    }*/
  }
}
