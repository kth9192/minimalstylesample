/*jshint esversion: 6 */

const Client = require('mongodb').MongoClient;
    express = require('express');
    bodyParser = require('body-parser');
    app = express();
    http = require('http').createServer(app);
    io = require('socket.io')(http);
    mime = require('mime');
    fs = require('fs');
    url = require('url');
    SelfReloadJSON = require('self-reload-json');
    obj = new SelfReloadJSON(__dirname + '/miniAppImage.json', 'utf8');
    mongoose = require('mongoose');
    imgDir = __dirname + '/images/';
    cart = require('./router_cart.js');
    // socketEvents = require('./socket.js')(io);

    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
      extended: true
    }));
    app.use(express.static(__dirname + '/' ));

    app.use('/cart', cart);

    app.get('/items', function(req, res){

      var resource = url.parse(req.url).pathname;

      fs.readFile('miniAppImage.json', 'utf8', (err, fileContent) => {
        if(err){
          console.log(err);
        }else {
          data = JSON.parse(fileContent.toString());
          res.json(data);
          res.end();
        }
      });
    });

    app.get('/image', function(req, res){

    var imgPath = imgDir + req.query.name + ".jpg";

    console.log('imgPath=' + imgPath);

    fs.readFile(imgPath, function (err, content) {
               if (err) {
                   res.writeHead(400, {'Content-type':'text/html'});
                   console.log(err);
                   res.end("No such image");
               } else {
                   //specify the content type in the response will be an image
                   res.writeHead(200,{'Content-type':'image/jpg'});
                   res.end(content);
               }
    });
  });

  app.get('/menu', function(req, res){
    Client.connect('mongodb://localhost:27017/menu', { useNewUrlParser: true }, function(error, database){
        if(error) {
            console.log(error);
        } else {
          db = database.db('menu');

                  // 1. 읽어올 document 필드값 정의
                 var query = {name:req.query.name};
                 // 2. find( ) 함수에 query 입력
                 db.collection('item').find(query).toArray(function(err, doc){
                        if(err){
                          console.log("에러 " + err);
                        }
                       if(doc != null){

                           console.log("쿼리확인 " + JSON.stringify(doc[0]));
                           res.json(doc);
                           res.end();
                        }
                     });
          database.close();
        }
      });
  });

  io.sockets.on('connection', (socket) => { // 웹소켓 연결 시
    console.log('connected!');

    socket.on('cartChange', (data) => { // 클라이언트에서 newScoreToServer 이벤트 요청 시
      socket.emit('cartChange', 'cartChange');
    });

    socket.on('hi', (data)=>{
      console.log(data);
    });

    socket.on('disconnect', function(){
      console.log('종료');
    });
  });

app.listen(3000, function(){
  console.log('server is running');
});

module.exports = app;
