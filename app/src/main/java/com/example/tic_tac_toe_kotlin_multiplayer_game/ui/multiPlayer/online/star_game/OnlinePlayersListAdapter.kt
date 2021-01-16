package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R


class OnlinePlayersListAdapter(private val playersList: MutableList<PlayerModel>, val clickingListener: ItemClickListener) :
    RecyclerView.Adapter<OnlinePlayersListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = playersList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.online_players_list_layout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var model: PlayerModel

        fun onBind() {
            model = playersList[adapterPosition]
            itemView.findViewById<TextView>(R.id.online_Player_Name_textView).text = model.name
//            itemView.Category_TextView_ID.text = model.capitalize()
            //Glide.with(itemView.context).load(BASE_IMG_URL + model.path).into(itemView.moviesImageViewID)
            itemView.setOnClickListener {
                clickingListener.viewClicked(adapterPosition)
            }
        }
    }
}