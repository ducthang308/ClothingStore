package Interface;

import java.util.List;

import DTO.MessageDTO;
import DTO.SendMessageRequest;
import Model.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiMessage {
    @POST("api/v1/messages")
    Call<Message> sendMessage(@Header("Authorization") String token, @Body SendMessageRequest request);

    @GET("api/v1/messages/{conversationId}")
    Call<List<Message>> getAllMessagesByConversationId(@Header("Authorization") String token, @Path("conversationId") int conversationId);

    @GET("api/v1/messages/receiverId/{receiverId}")
    Call<List<MessageDTO>> getAllMessagesByReceiverId(@Header("Authorization") String token, @Path("receiverId") int receiverId);
}
