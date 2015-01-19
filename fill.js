// clear
db.dropDatabase()

// fill
for (var i = 1; i <= 10000; i++) {
  db.users.insert({ name: "user-" + i, description: "description" })
}

// create index
db.users.ensureIndex({ name: 1 })
