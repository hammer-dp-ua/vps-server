Ext.define('VPSServer.view.fileslist.FilesController', {
   extend: 'Ext.app.ViewController',
   alias: 'controller.files',

   init: function() {
   },

   onAfterRender: function() {
      //this.getViewModel().getStore('viewModelFilesStore').load();
      this.getView().getStore('filesStore').load();
   },

   onClickDelete: function(grid, rowIndex, colIndex) {
      var store = grid.getStore();
      store.removeAt(rowIndex);
   },

   onClickDownload: function(grid, rowIndex, colIndex) {
      var store = grid.getStore();
      var fileName = store.getAt(rowIndex).get('name');

      window.open(VPSServer.view.main.MainController.FILES_URI + fileName);
   }
});