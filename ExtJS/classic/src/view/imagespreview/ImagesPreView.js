Ext.define('VPSServer.view.imagespreview.ImagesPreView', {
   extend: 'Ext.panel.Panel',

   requires: [
      'VPSServer.store.ImageFilesStore'
   ],

   alias: 'widget.imagespreview',
   title: 'Image files',
   reference: 'imagesPreView',
   closable: true,

   items: Ext.create('Ext.view.View', {
      store: {
         type: 'imageFilesStore'
      },

      tpl: [
         '<tpl for=".">',
            '<div class="" id="{name:stripTags}">',
               '<div class=""><img src="{url}" title="{name:htmlEncode}"></div>',
               '<span class="">{shortName:htmlEncode}</span>',
            '</div>',
         '</tpl>',
         '<div class=""></div>'
      ],
      multiSelect: true,
      height: 310,
      trackOver: true,
      overItemCls: 'x-item-over',
      itemSelector: 'div.thumb-wrap',
      emptyText: 'No images to display',
      listeners: {
         selectionchange: function (dv, nodes) {
            var l = nodes.length,
               s = l !== 1 ? 's' : '';
            this.up('panel').setTitle('Simple DataView (' + l + ' item' + s + ' selected)');
         }
      }
   })
});
