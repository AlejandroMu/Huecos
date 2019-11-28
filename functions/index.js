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

app.post('/like',(req,res)=>{
	var body=req.body;
	var user=body.user;
	var pub=body.pub;
	admin.database().ref().child("publications")
	.child('ALL').child(pub).once('value',(snap)=>{
		var val=snap.val();
		var likes=val.likes;
		if(likes==undefined){
			likes=[];
		}
		var msm='';
		if(likes.includes(user)){
			var i = likes.indexOf( user );
			msm='like eliminado';
			if ( i !== -1 ) {
				likes.splice( i, 1 );
			}
		}else{
			msm='like agregado';
			likes.push(user);
		}

		val.likes=likes;
		admin.database().ref().child("publications")
			.child('ALL').child(pub).set(val);
		res.status(200);
		res.send(msm);
		
		
	});

	
});

exports.functions = functions.https.onRequest(app);