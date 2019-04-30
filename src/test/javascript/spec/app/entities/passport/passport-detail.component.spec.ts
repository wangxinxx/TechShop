/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechShopTestModule } from '../../../test.module';
import { PassportDetailComponent } from 'app/entities/passport/passport-detail.component';
import { Passport } from 'app/shared/model/passport.model';

describe('Component Tests', () => {
    describe('Passport Management Detail Component', () => {
        let comp: PassportDetailComponent;
        let fixture: ComponentFixture<PassportDetailComponent>;
        const route = ({ data: of({ passport: new Passport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [PassportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PassportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PassportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.passport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
