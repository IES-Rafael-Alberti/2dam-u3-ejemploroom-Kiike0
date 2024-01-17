package com.example.ejemploroom.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemploroom.addtasks.domain.AddTaskUseCase
import com.example.ejemploroom.addtasks.domain.DeleteTaskUseCase
import com.example.ejemploroom.addtasks.domain.GetTasksUseCase
import com.example.ejemploroom.addtasks.domain.UpdateTaskUseCase
import com.example.ejemploroom.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.ejemploroom.addtasks.ui.TaskUiState.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//El parámetro getTasksUseCase del constructor se inyecta sin private val porque no nos hace falta
// ya que lo vamos a utilizar directamente en la variable uiState que gestionará los estados de la ui.
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    getTasksUseCase: GetTasksUseCase
): ViewModel() {

    //El caso de uso getTasksUseCase() nos devuelve el Flow continuo y cada vez que actualice
    //los datos va a pasarlo a Success (ver data class en TaskUiState).
    //Si por algún motivo falla y existe algún error, lo vamos a capturar y enviar al estado Error
    //con el parámetro de la excepción que ha generado.
    //El último modificador hará que cuando mi app o la pantalla esté en segundo plano hasta que no
    //pasen 5 segundos no bloqueará o cancelará el Flow (por defecto es 0)
    //Por ejemplo nuestra app pasará a segundo plano si nos llaman, o si desplegamos el menú superior
    //para ver una notificación o un whatsapp, etc.
    //Con stateIn, en el último argumento, también estamos asignando el estado inicial a Loading.
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _myTaskText = MutableLiveData<String>()
    val myTaskText: LiveData<String> = _myTaskText

    //private val _tasks = MutableLiveData<List<TaskModel>>()
    //val tasks: LiveData<List<TaskModel>> get() = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    //Actualizamos la función para crear la tarea en la lista anterior
    //Comentamos el mensaje al log que hemos realizado inicialmente y añadimos una nueva tarea a la lista _tasks
    fun onTaskCreated() {
        onDialogClose()

        //Un viewModelScope es una corutina.
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = _myTaskText.value ?: ""))
        }

        _myTaskText.value = ""
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onTaskTextChanged(taskText: String) {
        _myTaskText.value = taskText
    }

    fun onDeleteTask(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }

    /*
    fun onUpdateTask(taskModel: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(taskModel)
        }
    }
    */

    /*
    fun onItemRemove(taskModel: TaskModel) {

    }*/

    fun onCheckBoxSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            // Llama al caso de uso para actualizar la tarea en la base de datos
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

}