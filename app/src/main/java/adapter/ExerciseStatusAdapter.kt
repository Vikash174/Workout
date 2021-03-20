package adapter

import Model.ExerciseModel
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.R
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>, private val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false))

    }


    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvItem: TextView = view.tvItem

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()
        if (model.getIsSelected()) {
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_border)
            holder.tvItem.setTextColor(Color.BLACK)
        } else if (model.getIsCompleted()) {
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.WHITE)
        } else {
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_gray_background)
            holder.tvItem.setTextColor(Color.BLACK)
        }


    }

}