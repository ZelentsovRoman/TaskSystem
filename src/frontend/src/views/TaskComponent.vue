<template>
  <main id="Home-page">
    <div class="main">
      <div class="home">
        <div class="home-tab">
          <form>
            <h1>Задача {{ this.task.taskId }}</h1>
            <div class="date">
              <div class="column1">
                <span class="text">Статус</span>
                <span class="text">Дата создания</span>
                <span class="text">Создано</span>
                <span class="text" v-if="this.priveleges === 'User'">Приоритет</span>
                <span class="text" v-if="this.priveleges === 'User'">Исполнитель</span>
                <span class="text" v-if="this.priveleges === 'User'">Дата начала и окончания</span>
              </div>
              <div class="column2">
                <span :class="changeColor(status)" name="status">{{ status }}</span>
                <span class="text">{{ this.task.date.substring(0, 10) }}</span>
                <span class="text">{{ name }} {{ lastName }}</span>
                <span class="text" v-if="this.priveleges === 'User'" :style="{'color': changeColorPriority(task.priority)}">{{ task.priority }}</span>
                <span class="text" v-if="this.priveleges === 'User'">{{ selectedEmployee.name }} {{ selectedEmployee.lastName }}</span>
                <span class="text" v-if="this.priveleges === 'User'">{{ task.dateStart }} ~ {{ task.dateEnd }}</span>
              </div>
            </div>
            <span class="text" v-if="this.priveleges === 'Admin'">Приоритет</span>
            <select v-if="this.priveleges === 'Admin'" v-model="this.task.priority" class="selector" required>

              <option v-for="priority in priorities" :key="priority" :value="priority">{{ priority }}</option>
            </select>
            <span class="text" v-if="this.priveleges === 'Admin'">Исполнитель</span>
            <select v-if="this.priveleges === 'Admin'" v-model="selectedEmployee" class="selector" required>
              <option v-for="user in employee" :key="user.employeeId" :value="user">{{ user.name }} {{
                  user.lastName
                }}
              </option>
            </select>
            <span v-if="this.priveleges === 'Admin'" class="text">Дата начала и окончания</span>
            <date-picker v-if="this.priveleges === 'Admin'" v-model:value="date" aria-required="true" class="date-picker" format="DD-MM-YYYY"
                         range type="date" value-type="format" :disabled-date="disabledBeforeTodayAndWeekEnd" ></date-picker>
            <span class="text">Описание</span>
            <textarea v-model="task.description" class="input description" type="text"></textarea>
            <div class="subtasks">
              <div>
                <span class="text">Подзадачи</span>
                <div v-for="(subtask, subtaskId) in subtasks" v-bind:key="subtask.subtaskId">
                  <input v-model="subtask.value" type="checkbox">
                  <input v-model="subtask.description" type="text">
                  <button v-if="priveleges==='Admin'" class="material-icons" name="delete"
                          @click="deleteSubtask(subtaskId)">clear
                  </button>
                </div>
                <button name="add" type="button" @click="addSubtask" v-if="priveleges==='Admin'">Добавить подзадачу</button>
              </div>
              <div class="buttons">
                <button class="back" @click="navigate">Назад</button>
                <button v-if="priveleges==='Admin'" class="delete" @click="deleteTask">Удалить</button>
                <button @click="submit">Сохранить</button>
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
        description: '',
        statusId: '',
        date: '',
        userId: JSON.parse(localStorage.getItem('user')),
        priority:''
      },
      employee: [],
      subtasks: [],
      selectedEmployee: [],
      status: '',
      description: '',
      stat: '',
      newTask: '',
      name: '',
      lastName: '',
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
    fetch("/api/getTask/" + this.$route.params.id)
        .then((response) => response.json())
        .then((data) => {
          this.task = data
          this.name = this.task.userId.employee.name
          this.lastName = this.task.userId.employee.lastName
          this.selectedEmployee = this.task.employeeId
          this.status = this.task.statusId.status
          this.date.push(this.task.dateStart, this.task.dateEnd)
        }).then(
        fetch("/api/getSubtasks/" + this.$route.params.id)
            .then((response) => response.json())
            .then((data) => {
              this.subtasks = data
            })
    )
  },
  methods: {
    disabledBeforeTodayAndWeekEnd(date) {
      return date < new Date(new Date().setHours(0, 0, 0, 0)) || date.getDay()===0 || date.getDay()===6;
    },
    async deleteTask(e) {
      e.preventDefault()
      this.$router.push({path: '/'})
      await fetch("/api/deleteTask/" + this.$route.params.id, {
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

    },
    changeColorPriority(priority) {
      switch (priority){
        case 'Низкий': return 'green';
        case 'Средний': return 'orange';
        case 'Высокий': return 'red';
      }
    },
    changeColor(status) {
      if (status == 'Выполняется') {
        return "red"
      } else return "green"
    },
    navigate(e) {
      e.preventDefault()
      router.push({path: '/'})
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
    async submit(e) {
      e.preventDefault()
      if (this.date[0] !== undefined && this.date[0]!==null) {
        this.newTask = this.task
        this.newTask.dateStart = this.date[0]
        this.newTask.dateEnd = this.date[1]
        this.newTask.employeeId = this.selectedEmployee
        this.newTask.subtasks = this.subtasks
        var count = 0;
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
        router.push({path: '/'})
        await fetch("/api/saveTask", {
              body: this.newTask,
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
  },
  computed: {
    priveleges: function () {
      return JSON.parse(localStorage.getItem('user')).employee.privileges
    },
    disable() {
      var value = false
      if (this.priveleges === 'User') {
        value = true
      }
      return value
    }
  }
}
</script>