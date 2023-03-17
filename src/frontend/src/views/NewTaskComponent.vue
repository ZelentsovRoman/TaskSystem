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
            <span class="text">Приоритет</span>
            <select v-model="this.task.priority" class="selector" required>

              <option v-for="priority in priorities" :key="priority" :value="priority">{{ priority }}</option>
            </select>
            <span class="text">Дата начала и окончания</span>
            <date-picker v-model:value="date" aria-required="true" class="date-picker" format="DD-MM-YYYY" range
                         type="date" value-type="format" :disabled-date="disabledBeforeTodayAndWeekEnd"></date-picker>
            <span class="text">Описание</span>
            <textarea v-model="this.task.description" class="input description" type="text"></textarea>

            <div class="subtasks">
              <div>
                <span class="text">Подзадачи</span>
                <div v-for="(subtask, subtaskId) in task.subtasks" :key="subtask.subtaskId">
                  <input v-model="subtask.value" type="checkbox">
                  <input v-model="subtask.description" type="text">
                  <button class="material-icons" name="delete" @click="deleteSubtask(subtaskId)">clear</button>
                </div>
                <button name="add" type="button" @click="addSubtask">Добавить подзадачу</button>
              </div>
              <div class="buttons">
                <button class="back" @click="navigate">Назад</button>
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
import router from "@/router";

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
        priority:'',
        statusId: '',
        date: '',
        subtasks: [],
        userId: JSON.parse(localStorage.getItem('user'))
      },
      employee: [],
      selectedEmployee: [],
      stat: '',
      last: '',
      priorities:['Низкий', 'Средний','Высокий']
    }
  },
  async mounted() {
    await fetch("/api/allEmployee", {
          body: JSON.stringify(this.task.userId, null, 2),
          headers: {
            'Content-Type': 'application/json'
          },
          method: "post"
        }
    )
        .then((response) => response.json())
        .then((data) => {
          this.employee = data;
        })
    fetch("/api/getStatuses")
        .then((response) => response.json())
        .then((data) => {
          this.stat = data;
        })
  },
  methods: {
    disabledBeforeTodayAndWeekEnd(date) {
      return date < new Date(new Date().setHours(0, 0, 0, 0)) || date.getDay()===0 || date.getDay()===6;
    },
    navigate() {
      router.push({path: '/'})
    },
    deleteSubtask(subtaskId) {
      this.task.subtasks.splice(subtaskId, 1)
    },
    addSubtask() {
      this.task.subtasks.push({
        description: '',
        value: false,
        taskId: null
      })
    },
    async submit(e) {
      e.preventDefault()
      if (this.date[0] !== undefined && this.date[0]!==null) {
        router.push({path: '/'})
        this.task.dateStart = this.date[0]
        this.task.dateEnd = this.date[1]
        var d = new Date()
        this.task.date =  ("0" + d.getDate()).slice(-2) + "-" + ("0"+(d.getMonth()+1)).slice(-2) + "-" +
            d.getFullYear();
        var count = 0;
        this.task.subtasks.forEach(function (item) {
          if (!item.value) {
            count++
          }
        })
        if (count === 0 && this.task.subtasks.length > 0) {
          this.task.statusId = this.stat[1]
        } else this.task.statusId = this.stat[0]
        this.task = JSON.stringify(this.task, null, 2)
        await fetch("/api/saveNewTask", {
              body: this.task,
              headers: {
                'Content-Type': 'application/json'
              },
              method: "post"
            }
        ).then(async response => {
          if (response.ok) {
            location.reload()
          }
        })
      } else {
        alert('Выберите дату начала и окончания')
      }
    }
  }
}
</script>