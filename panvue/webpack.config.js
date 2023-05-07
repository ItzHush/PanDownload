// const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    // other configurations
    module: {
        rules: [
            // {
            //     // test: /\.(pdf|jpg|png|gif|svg|ico|txt)$/,
            //     test: /\.(PNG|JPG|GIF|SVG|pptx)$/,
            //     loader: 'url-loader',
            //     options: {
            //         name: '[name].[ext]',
            //         outputPath: 'D:/A_panDownload/files '
            //     }
            // },
            {test: /\.txt$/, use: 'raw-loader'},
            // {
            //     test: /\.docx$/,
            //     use: [
            //         {
            //             loader: 'file-loader',
            //             options: {
            //                 name: '[name].[ext]',
            //                 outputPath: 'D:/A_panDownload/files '
            //             }
            //         },
            //         {
            //             loader: 'extract-loader'
            //         },
            //         {
            //             loader: 'html-loader'
            //         },
            //         {
            //             loader: 'unzip-loader'
            //         }
            //     ]
            // }

            // other loaders
        ]
    },
    // other configurations

    plugins: [
        // new CopyWebpackPlugin([
        //     {
        //         from: "D:\\A_panDownload\\files",
        //         to: 'static',
        //         ignore: ['.*']
        //     }
        // ])
    ]
};
// const VueLoaderPlugin = require('vue-loader/lib/plugin')
//
// module.exports = {
//     // ...
//     module: {
//         rules: [
//             {
//                 test: /\.vue$/,
//                 loader: 'vue-loader'
//             },
//             {
//                 test: /\.css$/,
//                 use: ['style-loader','css-loader']
//             }
//         ]
//     },
//     plugins: [
//         new VueLoaderPlugin()
//     ]
// }