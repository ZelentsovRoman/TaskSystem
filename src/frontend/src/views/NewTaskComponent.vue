<template>
  <main id="Home-page">
    <div class="main">
      <div class="home">
        <div class="home-tab">
          <h1>Новая задача</h1>
          <form @submit="submit">
            <span class="text">Исполнитель</span>
            <select v-model="this.task.employeeId" class="selector" required>
              <option v-for="user in employee" :key="user.employeeId" :value="user">{{ user.name }} {{
                  user.lastName
                }}
              </option>
            </select>
            <span class="text">Дата начала и окончания</span>
            <date-picker v-model:value="date" type="datetime" range class="date-picker" value-type="format"
                         format="DD-MM-YYYY HH:mm:ss" aria-required="true"></date-picker>
            <span class="text">Описание</span>
            <textarea class="input description" type="text" v-model="this.task.description" required></textarea>

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
      task: {
        dateStart: '',
        dateEnd: '',
        employeeId: '',
        description: '',
        taskId: '',
        statusId: '',
        date: ''
      },
      employee: [],
      selectedEmployee: [],
      subtasks: [],
      stat: '',
      last: '',
    }
  },
  mounted() {
    fetch("/api/allEmployee")
        .then((response) => response.json())
        .then((data) => {
          this.employee = data;
        })
    fetch("/api/getLast")
        .then((response) => response.json())
        .then((data) => {
          this.last = data;
          this.task.taskId = this.last.taskId + 1
        })
    fetch("/api/getStatuses")
        .then((response) => response.json())
        .then((data) => {
          this.stat = data;
        })
  },
  methods: {
    navigate() {
      this.$router.push({ path: '/' })
    },
    deleteSubtask(subtaskId) {
      this.subtasks.splice(subtaskId, 1)
    },
    addSubtask() {
      this.subtasks.push({
        description: '',
        value: false,
        taskId: this.task.taskId
      })
    },
    async submit() {
      this.task.dateStart = this.date[0]
      this.task.dateEnd = this.date[1]
      this.task.date = new Date()
      var count = 0;
      this.subtasks.forEach(function (item) {
        if (!item.value) {
          count++
        }
      })
      if (count === 0 && this.subtasks.length>0) {
        this.task.statusId = this.stat[1]
      } else this.task.statusId = this.stat[0]
      this.task = JSON.stringify(this.task, null, 2)
      this.subtasks = JSON.stringify(this.subtasks, null, 2)
      this.$router.push({ path: '/' })
      await fetch("/api/saveTask", {
            body: this.task,
            headers: {
              'Content-Type': 'application/json'
            },
            method: "post"
          }
      ).then( await fetch("/api/saveSubtask", {
            body: this.subtasks,
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