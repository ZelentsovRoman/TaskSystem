<template>
  <main id="Home-page">
    <div class="main">
      <div class="home">
        <div class="home-tab">
          <form @submit="submit">
            <h1>Задача {{ this.task.taskId }}</h1>
            <div class="date">
              <div class="column1">
                <span class="text">Статус</span>
                <span class="text">Дата создания</span>
              </div>
              <div class="column2">
                <span :class="changeColor(status)" name="status">{{ status }}</span>
                <span class="text">{{ this.var.substring(0, 10) }}</span>
              </div>
            </div>
            <span class="text">Исполнитель</span>
            <select v-model="selectedEmployee" class="selector" required>
              <option v-for="user in employee" :key="user.employeeId" :value="user">{{ user.name }} {{
                  user.lastName
                }}
              </option>
            </select>
            <span class="text">Дата начала и окончания</span>
            <date-picker v-model:value="date" type="datetime" range class="date-picker" value-type="format"
                         format="DD-MM-YYYY HH:mm:ss" aria-required="true"></date-picker>
            <span class="text">Описание</span>
            <textarea class="input description" type="text" v-model="description" required></textarea>
            <div class="subtasks">
              <div>
                <span class="text">Подзадачи</span>
                <div v-for="(subtask, subtaskId) in subtasks" :key="subtask.subtaskId">
                  <input v-model="subtask.value" type="checkbox">
                  <input v-model="subtask.description" type="text">
                  <button class="material-icons" @click="deleteSubtask(subtaskId)" name="delete">clear</button>
                </div>
                <button @click="addSubtask" type="button" name="add">Добавить подзадачу</button>
              </div>
              <div class="buttons">
                <button @click="navigate" class="back">Назад</button>
                <button @click="deleteTask()" class="delete">Удалить</button>
                <button type="submit">Сохранить</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </main>
</template>
<style lang="scss" scoped>
@import "./src/views/css/task.scss";

.red {
  color: red;
}

.green {
  color: green;
}

h1 {
  font-size: 20pt;
}
</style>
<script>
import DatePicker from 'vue-datepicker-next';
import 'vue-datepicker-next/index.css';
import 'vue-datepicker-next/locale/ru';

export default {
  components: {DatePicker},
  data() {
    return {
      date: [],
      task: '',
      employee: [],
      selectedEmployee: [],
      subtasks: [],
      status: '',
      description: '',
      stat: '',
      newTask: '',
      var: ''
    }
  },
  mounted() {
    fetch("/api/allEmployee")
        .then((response) => response.json())
        .then((data) => {
          this.employee = data;
        })
    fetch("/api/getStatuses")
        .then((response) => response.json())
        .then((data) => {
          this.stat = data;
        })
    fetch("/api/getTask/" + this.$route.params.id)
        .then((response) => response.json())
        .then((data) => {
          this.task = data
          this.var = this.task.date
          this.selectedEmployee = this.task.employeeId
          this.status = this.task.statusId.status
          this.description = this.task.description
          this.date.push(this.task.dateStart, this.task.dateEnd)
        })
    fetch("/api/getSubtasks/" + this.$route.params.id)
        .then((response) => response.json())
        .then((data) => {
          this.subtasks = data
        })
  },
  methods: {
    async deleteTask() {
      var id = this.task.taskId
      this.$router.push({ path: '/' })
      await fetch("/api/deleteTask/" + id, {
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
    },
    changeColor(status) {
      if (status == 'Выполняется') {
        return "red"
      } else return "green"
    },
    navigate() {
      this.$router.push({ path: '/' })
    },
    addSubtask() {
      this.subtasks.push({
        description: '',
        value: false,
        taskId: this.task.taskId
      })
    },
    deleteSubtask(subtaskId) {
      this.subtasks.splice(subtaskId, 1)
    },
    async submit() {
      this.newTask = this.task
      this.newTask.dateStart = this.date[0]
      this.newTask.dateEnd = this.date[1]
      this.newTask.description = this.description
      this.newTask.employeeId = this.selectedEmployee
      var count = 0;
      var id = this.task.taskId
      if (this.subtasks.length > 0) {
        for (var element in this.subtasks) {
          if (!this.subtasks[element].value) {
            count++
          }
        }
        if (count === 0) {
          this.newTask.statusId = this.stat[1]
        } else this.newTask.statusId = this.stat[0]
      } else this.newTask.statusId = this.stat[0]
      this.newTask = JSON.stringify(this.task, null, 2)
      var s = JSON.stringify(this.subtasks, null, 2)
      this.$router.push({ path: '/' })
      await fetch("/api/saveSubtask/" + id, {
            body: s,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      )
          .then(await fetch("/api/saveTask", {
            body: this.newTask,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      ))
    }
  }
}
</script>