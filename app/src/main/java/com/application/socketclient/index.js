var socketIO = require('socket.io'),
    http = require('http'),
    port = process.env.PORT || 4000,
    ip = process.env.IP || '10.0.0.1', //My IP address. I try to "127.0.0.1" but it the same => don't run
    server = http.createServer().listen(port, ip, function() {
        console.log("IP = " , ip);
        console.log("start socket successfully");
});

io = socketIO.listen(server);
//io.set('match origin protocol', true);
io.set('origins', '*:*');

var userList=[];
var messageList=[];

var run = function(socket){
socket.broadcast.emit("message", "hello");

    socket.on("allmessage", function(id,value) {
        console.log(value);
        io.emit("allmessage",id,value);
    });

    socket.on("user-join", function(value) {
        console.log(value + "user-join");
        socket.broadcast.emit("new-users", value);

    });


    // recive data from sign up 

        socket.on("SignUpData", function(id,value) {
        console.log(value['username'] + "*******");
        userList.push(value);
        io.emit("SignUp",id, value);

    });

            // recive data from sign In 

        socket.on("SignInData", function(id,value) {
             console.log(value['emaill'] + "--------");
            for (let i = 0; i < userList.length; i++) {
                if (userList[i]['email']==value['emaill'] && userList[i]['password']==value['pass']) {
                 io.emit("SignInData",id, true,userList[i]);
                  break;
                }else if (i==userList.length-1) {
                  io.emit("SignInData",id, false,value);
    
                }
            }
            
     

    });

        socket.on("allusers", function(value) {
             console.log(userList+ "usersss");
        io.emit("allusers",userList);
        console.log(userList+ "////ussssss");


    });



  

}

io.sockets.on('connection', run);