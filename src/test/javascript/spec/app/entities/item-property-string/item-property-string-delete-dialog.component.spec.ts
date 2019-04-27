/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyStringDeleteDialogComponent } from 'app/entities/item-property-string/item-property-string-delete-dialog.component';
import { ItemPropertyStringService } from 'app/entities/item-property-string/item-property-string.service';

describe('Component Tests', () => {
    describe('ItemPropertyString Management Delete Component', () => {
        let comp: ItemPropertyStringDeleteDialogComponent;
        let fixture: ComponentFixture<ItemPropertyStringDeleteDialogComponent>;
        let service: ItemPropertyStringService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyStringDeleteDialogComponent]
            })
                .overrideTemplate(ItemPropertyStringDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyStringDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyStringService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
