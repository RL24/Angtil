import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { %COMPONENT%Component } from './%COMPONENT_NAME%.component';

describe('%COMPONENT%Component', () => {
    let component: %COMPONENT%Component;
    let fixture: ComponentFixture<%COMPONENT%Component>;

    beforeEach(
        async(() => {
            TestBed.configureTestingModule({
                declarations: [%COMPONENT%Component]
            }).compileComponents();
        })
    );

    beforeEach(() => {
        fixture = TestBed.createComponent(%COMPONENT%Component);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
