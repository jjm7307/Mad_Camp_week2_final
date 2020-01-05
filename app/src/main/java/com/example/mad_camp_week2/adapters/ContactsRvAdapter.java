package com.example.mad_camp_week2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_camp_week2.R;
import com.example.mad_camp_week2.models.ModelContacts;

import java.util.ArrayList;
import java.util.List;

public class ContactsRvAdapter extends RecyclerView.Adapter<ContactsRvAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ModelContacts> mListContacts;
    private List<ModelContacts> filteredContacts;

    String _name;

    public ContactsRvAdapter(Context context, List<ModelContacts> listContacts) {
        mListContacts = listContacts;
        filteredContacts = listContacts;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.items_contacts, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView contact_name, contact_number;
        ImageView contact_profile;

        contact_name = holder.contact_name;
        contact_number = holder.contact_number;
        contact_profile = holder.contact_profile;

        contact_profile.setImageResource(R.drawable.icon_profile_red);
        contact_name.setText(filteredContacts.get(position).getName());
        contact_number.setText(filteredContacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return filteredContacts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    filteredContacts = mListContacts;
                    Log.v("filter_ empty : ", " hi");
                } else {
                    ArrayList<ModelContacts> filteringList = new ArrayList<>();
                    for(ModelContacts name : mListContacts) {
                        _name = name.getName();
                        if(_name.toLowerCase().contains(charString.toLowerCase())){
                            Log.v("filter_ checking... : ", _name);
                            //modelContact 객체 내부의 String _name 이 같으면 ModelContact name 을 filtering list 에 추가
                            filteringList.add(name);
                        }
                    }
                    filteredContacts = filteringList;
                    Log.v("filtered list num : ", String.valueOf(filteredContacts.size()));
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredContacts;

                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredContacts = (ArrayList<ModelContacts>)results.values;
                notifyDataSetChanged();
                Log.v("filter_ changed complete : ", String.valueOf(filteredContacts.size()));
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contact_name, contact_number;
        ImageView contact_call, contact_profile;

        public ViewHolder(View itemView) {
            super(itemView);

            contact_profile = itemView.findViewById(R.id.profile);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_call = itemView.findViewById(R.id.calling);
        }
    }
}