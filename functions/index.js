const functions = require('firebase-functions');
const admin = require('firebase-admin');
const express = require('express');

const ITEMS = 20;

admin.initializeApp();
const app = express();
app.use(express.json());

app.get('/publications', (req, res) => {
	var publications = [];
	var out = [];
    var page = req.query.page;
    var range=req.query.range;
    var el=req.query.el;
    if(range===undefined){
        range=ITEMS;
    }
    if(el===undefined){
        el=0;
    }
    range=parseInt(range);
    page=parseInt(page);
    el=parseInt(el);
    
	admin.database().ref()
		.child('publications')
		.child('ALL')
		.once('value', function(snap){

			snap.forEach(
				(item) => {
					publications.push(item.val());														
				}
			);

			for(var i=page*ITEMS+el ; i < range+el + (page*ITEMS) ; i++){
				if(publications[i] !== undefined){
					out.push(publications[i]);
				}else{
                    break;
                }
			}

			res.send(out);

	});
	
});

app.get('/publications/like',(req,res)=>{
	var body=req.query;
	var user=body.user.toString().replace('_','@');
	var pub=body.pub;
	admin.database().ref().child("publications")
	.child('ALL').child(pub).once('value',(snap)=>{
		var val=snap.val();
		var likes=val.likes;
		if(likes==undefined){
			likes=[];
		}
		if(likes.includes(user)){
			var i = likes.indexOf( user );
			if ( i !== -1 ) {
				likes.splice( i, 1 );
			}
		}else{
			likes.push(user);
		}
		var state=val.state;
		var userPro=val.user;
		val.likes=likes;
		admin.database().ref().child("publications")
			.child('ALL').child(pub).set(val);
		
		admin.database().ref().child("publications")
			.child(state).child(userPro).child(pub).set(val);
		res.status(200);
		res.send(val);
		
		
	});

	
});

app.get('/publications/state',(req,res)=>{
	var pub=req.query.pub;
	var state=req.query.state;
	admin.database().ref().child("publications").child('ALL')
	.child(pub).once('value',(snap)=>{
		var publication=snap.val();
		var oldState=publication.state;
		publication.state=state;

		admin.database().ref().child("publications")
			.child('ALL').child(pub).set(publication);

		admin.database().ref().child("publications")
			.child(oldState).child(publication.user).child(pub).set(null);

		admin.database().ref().child("publications")
			.child(state).child(publication.user).child(pub).set(publication);


		res.send(publication);
	});
});

app.get('/messages',(req,res)=>{
	var user=req.query.user;
	var userDest=req.query.dest;
	var msms=[];
	admin.database().ref().child("messages").child(user).child(userDest)
	.once('value',snap=>{
		snap.forEach(item=>{
			msms.push(item.val());
		});
		msms.sort((a,b)=>{
			return a.date-b.date;
		});
		res.send(msms);
		
	});


});

exports.functions = functions.https.onRequest(app);