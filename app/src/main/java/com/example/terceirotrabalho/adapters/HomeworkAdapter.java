package com.example.terceirotrabalho.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.terceirotrabalho.DAO.HomeworkDao;
import com.example.terceirotrabalho.R;
import com.example.terceirotrabalho.activities.CreateHomeworkActivity;
import com.example.terceirotrabalho.activities.EditHomeworkActivity;
import com.example.terceirotrabalho.database.AppDatabase;
import com.example.terceirotrabalho.model.Homework;

import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<Homework> {
    private List<Homework> homeworkList;
    private Context context;
    private HomeworkDao homeworkDao;

    public HomeworkAdapter(@NonNull Context context, List<Homework> homeworkList) {
        super(context, 0,homeworkList);
        this.context = context;
        this.homeworkList = homeworkList;
        this.homeworkDao = AppDatabase.getAppDatabase(context).homeworkDao();
    }

    public void addHomework(Homework homework) {
        homework.setId((int) homework.getHomeworkId()); // Atualiza o ID do objeto Homework com o valor gerado
        homeworkList.add(homework);
        notifyDataSetChanged(); // Notifica o adaptador sobre a adição
    }

    public void atualizarDados(List<Homework> homeworkList) {
        this.homeworkList.clear();
        this.homeworkList.addAll(homeworkList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Homework homework = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_homework, parent, false);
        }

        TextView textViewHomeworkName = convertView.findViewById(R.id.homeworkNameHome);
        TextView textViewHomeworkDescription = convertView.findViewById(R.id.homeworkDescriptionHome);
        TextView textViewHomeworkDate = convertView.findViewById(R.id.homeworkDateHome);
        TextView textViewHomeworkTime = convertView.findViewById(R.id.homeworkTimeHome);
        TextView textViewHomeworkDone = convertView.findViewById(R.id.homeworkDone);
        Button buttonDeleteHomework = convertView.findViewById(R.id.buttonDeleteHomework);
        Button buttonEditHomework = convertView.findViewById(R.id.buttonEditHomework);
        Button buttonFinishedHomework = convertView.findViewById(R.id.buttonMarkHomeworkAsDone);

        textViewHomeworkName.setText("Nome da atividade: " + homework.getHomeworkName());
        textViewHomeworkDescription.setText("Descrição da atividade: " + homework.getHomeworkDescription());
        textViewHomeworkDate.setText("Data e hora prazo da atividade: " + homework.formatDateAndTime());
        textViewHomeworkDone.setText("Atividade concluída: " + (homework.finished == true ? "✅" : "❌"));

        buttonEditHomework.setOnClickListener(view -> {
            Intent it_edit_homework = new Intent(context, EditHomeworkActivity.class);
            it_edit_homework.putExtra("homeworkId", homework.getHomeworkId());
            it_edit_homework.putExtra("homeworkName", homework.getHomeworkName());
            it_edit_homework.putExtra("homeworkDescription", homework.getHomeworkDescription());

            ((Activity) context).startActivityForResult(it_edit_homework, CreateHomeworkActivity.REQUEST_CODE_EDIT_HOMEWORK);
        });

        buttonDeleteHomework.setOnClickListener(view -> {
            homeworkDao.deleteHomework(homework);
            homeworkList.remove(position);
            notifyDataSetChanged();
        });

        buttonFinishedHomework.setOnClickListener(view -> {
            homeworkDao.updateHomeworkFinishedStatus(homework.homeworkId, true);
            homework.setFinished(true);
            notifyDataSetChanged();
        });

        return convertView;
    }
}