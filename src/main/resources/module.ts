import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { %MODULE%Component } from './%MODULE_NAME%.component';

const ROUTES: Routes = [
    {
        path: '',
        component: %MODULE%Component
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(ROUTES)
    ],
    declarations: [
        %MODULE%Component
    ]
})
export class %MODULE%Module {}
