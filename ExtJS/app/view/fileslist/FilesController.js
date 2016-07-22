Ext.define('VPSServer.view.fileslist.FilesController', {
   extend: 'Ext.app.ViewController',
   alias: 'controller.files',

   init: function() {
   },

   onAfterRender: function() {
      //this.getViewModel().getStore('viewModelFilesStore').load();
      this.getView().getStore('videoFilesStore').load();
   },

   onClickDelete: function(grid, rowIndex, colIndex) {
      var store = grid.getStore();
      store.removeAt(rowIndex);
   },

   onClickDownload: function(grid, rowIndex, colIndex) {
      var store = grid.getStore();
      var fileName = store.getAt(rowIndex).get('name');

      window.open(VPSServer.view.main.MainController.VIDEO_FILES_URI + fileName);
   },

   onClickOpenImagesPreview: function(grid, rowIndex, colIndex) {
      var store = grid.getStore();
      var fileName = store.getAt(rowIndex).get('name');

      this.getView().hide();
      Ext.GlobalEvents.fireEventArgs('displayImageFiles', [this.getView(), fileName]);
   }
});