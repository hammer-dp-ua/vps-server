Ext.define('VPSServer.view.fileslist.FilesController', {
   extend: 'Ext.app.ViewController',
   alias: 'controller.files',

   init: function() {
   },

   onAfterRender: function() {
      this.getViewModel().getStore('viewModelFilesStore').load();
   },

   onClickDelete: function(grid, rowIndex, colIndex) {
      var store = grid.getStore();
      store.removeAt(rowIndex);
      store.sync();
   }
});