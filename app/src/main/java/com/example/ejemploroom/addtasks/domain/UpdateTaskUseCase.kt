package com.example.ejemploroom.addtasks.domain

import com.example.ejemploroom.addtasks.data.TaskRepository
import com.example.ejemploroom.addtasks.ui.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.updateTask(taskModel)
    }
}