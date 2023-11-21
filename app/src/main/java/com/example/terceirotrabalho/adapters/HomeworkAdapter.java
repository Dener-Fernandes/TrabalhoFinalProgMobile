package com.example.terceirotrabalho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.terceirotrabalho.R;
import com.example.terceirotrabalho.model.Homework;

import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<Homework> {
    private List<Homework> homeworkList;
    private Context context;

    public HomeworkAdapter(@NonNull Context context, List<Homework> homeworkList) {
        super(context, 0,homeworkList);
        this.context = context;
        this.homeworkList = homeworkList;
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

        textViewHomeworkName.setText("Nome da atividade: " + homework.getHomeworkName());
        textViewHomeworkDescription.setText("Descrição da atividade: " + homework.getHomeworkDescription());
        textViewHomeworkDate.setText("Data prazo da atividade: " + homework.getHomeworkDate());
        textViewHomeworkTime.setText("Hora prazo da atividade: " + homework.getHomeworkTime());

        return convertView;
    }
}
