package com.rahul.roomdatabasemvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rahul.roomdatabasemvvm.model.UserModal;

public class UserRVAdapter extends ListAdapter<UserModal, UserRVAdapter.ViewHolder> {

    private OnItemClickListener listener;
    Context context;

    public UserRVAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<UserModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserModal>() {
        @Override
        public boolean areItemsTheSame(UserModal oldItem, UserModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(UserModal oldItem, UserModal newItem) {
            return oldItem.getUserName().equals(newItem.getUserName()) &&
                    oldItem.getUserNumber().equals(newItem.getUserNumber()) &&
                    oldItem.getUserDate().equals(newItem.getUserDate());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_layout, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModal model = getCourseAt(position);
        holder.courseNameTV.setText(model.getUserName());
        holder.courseDescTV.setText(model.getUserNumber());
        holder.courseDurationTV.setText(model.getUserDate());
//        holder.img_profile.setImageBitmap(BitmapManager.byteToBitmap(model.getImage()));
        Glide.with(context).load(model.getImage()).dontTransform().into(holder.img_profile);


    }

    public UserModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseNameTV, courseDescTV, courseDurationTV;
        ImageView img_profile;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);
            img_profile = itemView.findViewById(R.id.profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserModal model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}