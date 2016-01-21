Ext.define('VPSServer.store.FilesStore', {
   extend: 'Ext.data.Store',
   model: 'VPSServer.model.FileModel',

   data : [
      {name: '123', creationDate: '2016', size: 999},
      {name: '456', creationDate: '2016', size: 998},
      {name: '789', creationDate: '2016', size: 997},
      {name: '101112', creationDate: '2016', size: 996}
   ]
});