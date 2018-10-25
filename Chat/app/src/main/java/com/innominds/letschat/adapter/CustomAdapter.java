package com.innominds.letschat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.innominds.letschat.R;
import com.innominds.letschat.chat.ChatActivity;
import com.innominds.letschat.models.User;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    //Creating an arraylist of POJO objects
    private ArrayList<User> list_members=new ArrayList<>();
    private final LayoutInflater inflater;
    View view;
    MyViewHolder holder;
    private Context context;


    public CustomAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }


    //This method inflates view present in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.user_row, parent, false);
        holder=new MyViewHolder(view);
        return holder;
    }

    //Binding the data using get() method of POJO object
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User userData=list_members.get(position);
        //User Name
        holder.txt_UserName.setText(userData.getUserName());
        //User Jit_Id
        holder.txt_JitId.setText(userData.getUser_JitId());
        //holder.time.setText(list_items.getTime());

        holder.bindContact(userData);
    }

    //Setting the arraylist
    public void setListContent(ArrayList<User> list_members){
        this.list_members=list_members;
        notifyItemRangeChanged(0,list_members.size());

    }


    @Override
    public int getItemCount() {
        return list_members.size();
    }


    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txt_UserName,txt_JitId,time;
        User mUser;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_UserName=(TextView)itemView.findViewById(R.id.user_name);
            txt_JitId=(TextView)itemView.findViewById(R.id.content);
            time=(TextView)itemView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View v) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Inside here we start the chat activity
                    Intent intent = new Intent(context
                            ,ChatActivity.class);
                   // intent.putExtra("EXTRA_CONTACT_JID",mUser.user_JitId);
                    context.startActivity(intent);

                }
            });
        }

        public void bindContact( User user)
        {
            mUser = user;
            if (mUser == null)
            {
                Log.d(TAG,"Trying to work on a null Contact object ,returning.");
                return;
            }


        }
    }

    public void removeAt(int position) {
        list_members.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, list_members.size());
    }

}
