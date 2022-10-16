package mtproto.handle;

import java.io.UnsupportedEncodingException;

import mtproto.MTProtoConnection;
import mtproto.Response;
import mtproto.EncryptedResponse;
import mtproto.recieve.RecieveRPCError;
import mtproto.send.service.SendMsgsAck;
import mtproto.CombinatorIds;
import mtproto.RPCResponse;

public class HandleRecieveRPCError extends MTProtoCallback {
  public HandleRecieveRPCError (MTProtoConnection connection) {
    super(CombinatorIds.rpc_error, connection);
  }
  
  public void execute(Response response) {
    RPCResponse rpc_response = (RPCResponse)response;
    RecieveRPCError recieve_rpc_error = RecieveRPCError.from_rpc_response(rpc_response);
    //We should handle 303 errors for switching DC, for example: PHONE_MIGRATE_2
    //this should throw something to trigger a reinitialization of the MTProto client
    try {
      System.out.println("ERROR: RPC "+Integer.toString(recieve_rpc_error.error_code)+": "+recieve_rpc_error.error_message.ascii_string());
    } catch (UnsupportedEncodingException e) {
      System.out.println("ERROR: RPC "+Integer.toString(recieve_rpc_error.error_code)+": <invalid encoding>");
    }
  }
}
