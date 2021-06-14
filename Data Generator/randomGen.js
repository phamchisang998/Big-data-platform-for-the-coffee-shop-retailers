const fs = require('fs');
var stream = require('stream')
var Transform = stream.Transform;

class myTf extends Transform{
    constructor(...extra){
        super();
        this._extra = extra;
    }
    // emit(ev,...args) {
    //     if(ev === 'readable') return super.emit(ev,...args);
    //     super.emit(ev,...args);
    // }
    _transform (chunk, encoding, done) {
        var data = chunk.toString()
        if (this._lastLineData) data = this._lastLineData + data
   
        var lines = data.split('\n')
        this._lastLineData = lines.splice(lines.length-1,1)[0]
        var self = this;
        lines.forEach(line => this.push.bind(this)(`${self._extra.join(',')},${line} \n`))
        done()
    }
    _flush (done) {
        if (this._lastLineData) this.push(this._lastLineData)
        this._lastLineData = null
        done()
   }
}


require('events').EventEmitter.prototype._maxListeners = 0;
//requiring path and fs modulesr 
for(let dir of ['5-7']){
    fs.readdir( dir,function (err, files) {
        //handling error
        if (err) {
            return console.log('Unable to scan directory: ' + err);
        } 
        //listing all files using forEach
        files.forEach(function (file) {
            let content = file.split('-');
            let shop = content[1];
            let dt = content[2]
            let readStream = fs.createReadStream(`${dir}/${file}`)
            let liner = new myTf(shop,dt);

            readStream
            .pipe(liner)
            .pipe(fs.createWriteStream('./output.csv',{flags:'a'}))
        });
    });
}
