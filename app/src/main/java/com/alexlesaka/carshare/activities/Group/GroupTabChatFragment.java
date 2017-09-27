package com.alexlesaka.carshare.activities.Group;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.alexlesaka.carshare.R;
import com.alexlesaka.carshare.controllers.MainController;
import com.alexlesaka.carshare.adapters.ChatMessageFirebaseAdapter;


public class GroupTabChatFragment extends Fragment {

    private String groupId;
    private String currentUsername;


    private MainController mainController;

    //Messages List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private EditText sendText;
    private Button sendButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_tab_chat, container, false);

        mainController = ((GroupActivity) getActivity()).getMainController();
        groupId = ((GroupActivity) getActivity()).getGroupId();
        currentUsername = mainController.getLoginController().getUsername();

        //Graphics
        sendText = (EditText) view.findViewById(R.id.group_chat_input);
        sendButton = (Button) view.findViewById(R.id.group_chat_sendButton);


        //SendMessageListener
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = sendText.getText().toString();
                if (message.length() > 0) {
                    mainController.getGroupController().sendMessageToGroup(currentUsername, groupId, message);
                    sendText.setText("");
                    //recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);

                    //Cerrar teclado
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
            }
        });




        //Chat messages list
        recyclerView = (RecyclerView) view.findViewById(R.id.group_chat_recycler_view);
        ChatMessageFirebaseAdapter chatAdapter = new ChatMessageFirebaseAdapter(getActivity().getApplicationContext(), mainController.getGroupController().getGroupChatMessagesRef(groupId));
        recyclerView.setAdapter(chatAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }


}
