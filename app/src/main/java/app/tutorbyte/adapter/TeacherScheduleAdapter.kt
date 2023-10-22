package app.tutorbyte.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.R
import app.tutorbyte.helper.Schedule
import com.bumptech.glide.Glide

class TeacherScheduleAdapter(
    private var listofSchedules:List<Schedule>
): RecyclerView.Adapter<TeacherScheduleAdapter.TeacherViewHolder>() {


    class TeacherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image:ImageView = itemView.findViewById(R.id.scheduleImageView)
        val teacherName:TextView = itemView.findViewById(R.id.teacherNameTv)
        val teacherEcode:TextView = itemView.findViewById(R.id.teacherEcodeTv)
        val teacherRole:TextView = itemView.findViewById(R.id.teacherRole)
        val teacherRoom:TextView = itemView.findViewById(R.id.teacherRoomNo)
        val teacherEmail:TextView = itemView.findViewById(R.id.teacherEmail)
        val teacherPhone:TextView = itemView.findViewById(R.id.teacherPhone)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout_teacher,parent,false)
        return TeacherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofSchedules.size
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val currSchedule = listofSchedules[position]
        Glide.with(holder.itemView.context)
            .load(currSchedule.teacherTimetable)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.image);

        holder.teacherName.text = "Teacher Name: ${currSchedule.teacherName}"
        holder.teacherEcode.text = "Teacher e-code: ${currSchedule.teacherEcode}"
        holder.teacherRole.text = "Teacher Responsibility: ${currSchedule.teacherRole}"
        holder.teacherRoom.text = "Teacher sitting room: ${currSchedule.teacherSittingRoom}"
        holder.teacherEmail.text = "Teacher email-id: ${currSchedule.teacherEmail}"
        holder.teacherPhone.text = "Teacher contact: ${currSchedule.teacherContact}"

    }

}