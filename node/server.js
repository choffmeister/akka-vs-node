var mongo = require('mongodb').MongoClient;

mongo.connect('mongodb://localhost:27017/akkavsnode', function(err, db) {
  var http = require('http');

  http.createServer(function (req, res) {
    if (req.url === '/api/user') {
      var i = Math.ceil(Math.random() * 10000);
      db.collection('users').findOne({ name: 'user-' + i }, function (err, user) {
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(user, true, 2));
      });
    } else {
      res.writeHead(404, {'Content-Type': 'application/json'});
      res.end();
    }
  }).listen(8081, '0.0.0.0');
});
