/*jshint esversion: 6 */

const express = require('express');
      router = express.Router();
      Client = require('mongodb').MongoClient;

      router.get('/', function(req, res){
        Client.connect('mongodb://localhost:27017/cart', { useNewUrlParser: true }, function(error, database){
            if(error) {
                console.log(error);
            } else {
              db = database.db('cart');

              var query = {user : req.query.user};

              db.collection('cart').find(query).toArray( function(err, doc){
                     if(err){
                       console.log("에러 " + err);
                     }
                     if(doc == ''){
                       return res.status(400).end();
                     }else if(doc != null){

                        console.log("쿼리확인 " + doc);
                        res.json(doc);
                        res.end();
                     }

                  });
            database.close();
            }
          });
      });

      router.post('/modify', function(req, res){
      //아이템 수량과 종류를 받아 쿼리
        var db;

        Client.connect('mongodb://localhost:27017/cart', { useNewUrlParser: true }, function(error, database){
            if(error) {
                console.log(error);
            } else {

              db = database.db('cart');
              var query = {user : req.body.user};
              if(!query){
                return res.status(400).end();
              }

              db.collection('cart').find(query).toArray(function(err, doc){

                     if(err){
                       console.log("에러 " + err);
                     }

                     if(doc == ''){
                       console.log('db 에러');
                       return res.status(400).end();
                     }else if(doc != null){

                        var modifyQuery = {user : req.body.user, "items.name" : req.body.name };
                        var modifyOperator = null;

                        if (req.body.mode === "true") {
                           modifyOperator = {$inc : {'items.$.total': 1  }};
                        } else if(req.body.mode === "false"){
                          modifyOperator = {$inc : {'items.$.total': -1  }};
                        }

                        // console.log("name : " + req.body.name + " price : " + req.body.price);
                        var tmp = {
                            name : req.body.name,
                            total: 1,
                            price : parseInt(req.body.price)
                          };

                          // var operator = {$addToSet : {items: tmp}}; //addtoset은 tmp 와 완전히 같은 객체가 있는지를 검사.
                          // var query = {user : req.query.user};

                          db.collection('cart').updateOne(modifyQuery,modifyOperator,function(err,upserted){
                                if(err){
                                    console.log(err);
                                }else{
                                  // console.log(upserted);
                                  if(upserted.modifiedCount == 0){
                                    console.log("no exist!");

                                    var addQuery = {user : 'tester'};
                                    var addOperator = {$addToSet : {items: tmp}}; //addtoset은 tmp 와 완전히 같은 객체가 있는지를 검사.

                                    db.collection('cart').updateOne(addQuery, addOperator,  function(err){
                                      console.log(err);
                                    });
                                  }
                                  console.log('updated successfully!');
                                }
                            });

                            var modifyTotalQuery = {user : req.body.user};
                            var modifyTotalOperator = null;

                            if (req.body.mode === "true") {
                               modifyTotalOperator = {$inc : {'cartPrice': parseInt(req.body.price)  }};
                            } else if(req.body.mode === "false"){
                              modifyTotalOperator = {$inc : {'cartPrice': -parseInt(req.body.price)  }};
                            }

                            db.collection('cart').updateOne(modifyTotalQuery, modifyTotalOperator, function(err, upserted){
                              if(err){
                                  console.log(err);
                              }else{
                                console.log('totlaprice updated successfully!');
                              }
                            });

                            // console.log(tmp);
                            res.end('send OK');
                     }
                  });
            }
          });

        });

module.exports = router;
