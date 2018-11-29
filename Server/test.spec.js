/*jshint esversion: 6 */

const should = require('should');
const request = require('supertest');
const app = require('./server_minimalstyleapp.js');

describe('GET /cart', () => {
  describe('성공', () => {
    it('장바구니 조회', (done) => {
      request(app)
      .get('/cart?user=tester')
      .end((err, res) => {

        done();

      });
    });
  });
});

  describe('실패', () => {
    it('cart is null', done =>{
      request(app)
      .get('/cart?user=sowhat')
      .expect(400)
      .end(done);

    });
  });

  describe('POST /cart', () => {
    // describe('성공', () => {
    //
    // });
    describe('실패', () => {
      it('user error', (done) => {
        request(app)
        .post('/cart?')
        .send({user: ''})
        .expect(400)
        .end(done);
      });
    });
  });
