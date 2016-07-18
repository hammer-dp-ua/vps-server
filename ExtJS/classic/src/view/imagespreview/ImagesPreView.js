Ext.define('VPSServer.view.imagespreview.ImagesPreView', {
   extend: 'Ext.panel.Panel',

   requires: [
      'VPSServer.store.ImageFilesStore'
   ],

   alias: 'widget.imagespreview',
   title: '',

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
         '<div class="x-clear"></div>'
      ],
      multiSelect: true,
      height: 310,
      trackOver: true,
      overItemCls: 'x-item-over',
      itemSelector: 'div.thumb-wrap',
      emptyText: 'No images to display',
      plugins: [
         Ext.create('Ext.ux.DataView.DragSelector', {}),
         Ext.create('Ext.ux.DataView.LabelEditor', {dataIndex: 'name'})
      ],
      prepareData: function (data) {
         Ext.apply(data, {
            shortName: Ext.util.Format.ellipsis(data.name, 15),
            sizeString: Ext.util.Format.fileSize(data.size),
            dateString: Ext.util.Format.date(data.lastmod, "m/d/Y g:i a")
         });
         return data;
      },
      listeners: {
         selectionchange: function (dv, nodes) {
            var l = nodes.length,
               s = l !== 1 ? 's' : '';
            this.up('panel').setTitle('Simple DataView (' + l + ' item' + s + ' selected)');
         }
      }
   })
});
