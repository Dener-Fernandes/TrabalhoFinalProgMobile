package com.example.terceirotrabalho.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "parent", foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "user_id",
                childColumns = "fk_user_id",
                onDelete = CASCADE)
})
public class Parent {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "parent_id")
    public int parentId;

    @ColumnInfo(name = "fk_user_id")
    public int fkUserId;

    public Parent(int fkUserId) {
        this.fkUserId = fkUserId;
    }
}
