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
					publications.push({item: item.val(),page:page,range:range,el:el} );														
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

exports.functions = functions.https.onRequest(app);