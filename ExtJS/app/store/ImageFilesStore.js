Ext.define('VPSServer.store.ImageFilesStore', {
    extend: 'Ext.data.Store',

    alias: 'store.imageFilesStore',

    autoSync: true,
    proxy: {
        type: 'rest',
        url: VPSServer.view.main.MainController.IMAGE_FILES_URI,
        reader: {
            type : 'json'
        }
    }
});