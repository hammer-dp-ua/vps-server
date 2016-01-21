Ext.define('VPSServer.view.fileslist.FilesView', {
   extend: 'Ext.grid.Panel',

   requires: [
      'VPSServer.view.fileslist.FilesController'
   ],
   controller: 'files',

   alias: 'widget.gridfiles',
   title: 'Uploaded files',

   columns: [
      {text: "Name", dataIndex: "name", sortable: true},
      {text: "Creation date", dataIndex: "creationDate", sortable: true},
      {text: "Size", dataIndex: "size", sortable: true},
      {text: "Download", sortable: false},
      {text: "", width: 30, sortable: false}
   ]
});