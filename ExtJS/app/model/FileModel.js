Ext.define('VPSServer.model.FileModel', {
   extend: 'Ext.data.Model',

   fields: [
      {name: 'name',          type: 'string'},
      {name: 'creationDate',  type: 'string'},
      {
         name: 'size',
         type: 'float',
         sortType: 'asFloat',
         convert: function (val) {
            if (val) {
               return parseFloat(val / 1024 / 1024).toFixed(2);
            } else {
               return 0;
            }
         }
      }
   ],
   idProperty : 'name'
});
