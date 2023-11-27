package com.example.terceirotrabalho.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "parent", foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "user_id",
                childColumns = "fk_user_parent_id",
                onDelete = CASCADE)
})
public class Parent {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "parent_id")
    public int parentId;

    @ColumnInfo(name = "fk_user_parent_id")
    public int fkUserParentId;

    public Parent(int fkUserParentId) {
        this.fkUserParentId = fkUserParentId;
    }

    public int getParentId() {
        return parentId;
    }

    public int getFkUserId() {
        return fkUserParentId;
    }
}
