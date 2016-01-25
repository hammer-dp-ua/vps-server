Ext.define('VPSServer.model.FileModel', {
   extend: 'Ext.data.Model',

   fields: [
      {name: 'name',          type: 'string'},
      {name: 'creationDate',  type: 'string'},
      {name: 'size',          type: 'int'}
   ],
   idProperty : 'name'
});
