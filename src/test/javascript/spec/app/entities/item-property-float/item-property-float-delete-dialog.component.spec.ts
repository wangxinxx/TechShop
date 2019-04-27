/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyFloatDeleteDialogComponent } from 'app/entities/item-property-float/item-property-float-delete-dialog.component';
import { ItemPropertyFloatService } from 'app/entities/item-property-float/item-property-float.service';

describe('Component Tests', () => {
    describe('ItemPropertyFloat Management Delete Component', () => {
        let comp: ItemPropertyFloatDeleteDialogComponent;
        let fixture: ComponentFixture<ItemPropertyFloatDeleteDialogComponent>;
        let service: ItemPropertyFloatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyFloatDeleteDialogComponent]
            })
                .overrideTemplate(ItemPropertyFloatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyFloatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyFloatService);
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
